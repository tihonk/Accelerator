package com.accelerator.services.implementations;

import com.accelerator.services.DsspThirdPartyService;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

import static java.lang.String.format;
import static org.apache.http.entity.ContentType.create;

@Service("dsspThirdPartyService")
public class DsspThirdPartyServiceImpl implements DsspThirdPartyService {

    private CloseableHttpClient httpClient;
    private String resultId = "";

    private static final String DSSP_SERVER_URL =  "https://www3.cmbi.umcn.nl/xssp/";
    private static final String DSSP_STATUS_URL =  "https://www3.cmbi.umcn.nl/xssp/api/status/pdb_file/dssp/%s/";
    private static final String DSSP_RESULT_URL =  "https://www3.cmbi.umcn.nl/xssp/api/result/pdb_file/dssp/%s/";
    private static final String CONTENT_TYPE_PDB_FILE =  "chemical/x-pdb";
    private static final String PDB_CONTENT_FILE_PATH = "src/main/resources/pdbContext.txt";
    private static final String LOCATION = "Location";
    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String STATUS_JSON_PARAM = "status";
    private static final String RESULT_JSON_PARAM = "result";
    private static final String RESNUM = "RESNUM";
    private static final String CSRF_TOKEN_FIND_VALUE =  "id=\"csrf_token\"";
    private static final Integer CSRF_TOKEN_START_INDEX = 55;
    private static final Integer REDIRECT_STATUS = 302;
    private static final Integer REDIRECT_URL_LENGTH = 51;
    private static final Integer START_DSSP_CONTEXT = 77;

    private static final String OUTPUT_TYPE_KAY = "output_type";
    private static final String OUTPUT_TYPE_VALUE = "dssp";
    private static final String INPUT_TYPE_KAY = "input_type";
    private static final String INPUT_TYPE_VALUE = "pdb_file";
    private static final String CSRF_TOKEN_KAY = "csrf_token";
    private static final String FILE_KAY = "file_";

    @Override
    public String getDsspContext(MultipartFile pdbFile) throws IOException {
        httpClient = HttpClients.createDefault();
        String csrfToken = getRequestGetCsrfToken();
        resultId = postRequestGetDsspContextUrl(csrfToken, pdbFile);
        String dsspContext = getRequestGetDsspContext();
        httpClient.close();

        return dsspContext;
    }

    private String getRequestGetCsrfToken() throws IOException {
        HttpGet request = new HttpGet(DSSP_SERVER_URL);
        HttpResponse response = httpClient.execute(request);
        String responseBody = new BasicResponseHandler().handleResponse(response);
        return getCsrfToken(responseBody);
    }

    private String getCsrfToken(String responseBody) {
        int startCSRFToken = responseBody.indexOf(CSRF_TOKEN_FIND_VALUE) + CSRF_TOKEN_START_INDEX;
        String startCSRFTokenSubstring = responseBody.substring(startCSRFToken);
        int endCSRFToken = startCSRFTokenSubstring.indexOf("\"");
        return startCSRFTokenSubstring.substring(0, endCSRFToken);
    }

    private String postRequestGetDsspContextUrl(String csrfToken, MultipartFile pdbFile) throws IOException {
        HttpPost request = new HttpPost(DSSP_SERVER_URL);
        HttpResponse response = httpClient.execute(preparePostRequest(request, csrfToken, pdbFile));
        if (REDIRECT_STATUS == response.getStatusLine().getStatusCode()) {
            return getRedirectedResultId(response);
        }
        return null;
    }

    private HttpPost preparePostRequest(HttpPost request, String csrfToken, MultipartFile pdbFile) throws IOException {
        HttpEntity multipartEntity = getHttpEntity(csrfToken, pdbFile);
        request.setEntity(multipartEntity);
        return request;
    }

    private HttpEntity getHttpEntity(String csrfToken, MultipartFile pdbFile) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody(OUTPUT_TYPE_KAY, OUTPUT_TYPE_VALUE);
        builder.addTextBody(INPUT_TYPE_KAY, INPUT_TYPE_VALUE);
        builder.addTextBody(CSRF_TOKEN_KAY, csrfToken);
        setPdbFileToBuilder(builder, pdbFile);
        return builder.build();
    }

    private void setPdbFileToBuilder(MultipartEntityBuilder builder, MultipartFile pdbFile) throws IOException {
        File file = convertMultipartToFile(pdbFile);
        String fileName = pdbFile.getOriginalFilename();
        builder.addBinaryBody(FILE_KAY, file, create(CONTENT_TYPE_PDB_FILE), fileName);
    }

    private File convertMultipartToFile(MultipartFile pdbFile) throws IOException {
        InputStream initialStream = pdbFile.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File file = new File(PDB_CONTENT_FILE_PATH);
        try (OutputStream outStream = new FileOutputStream(file)) {
            outStream.write(buffer);
        }
        return file;
    }

    private String getRedirectedResultId(HttpResponse response) {
        HeaderElement[] headerLocationElements = response.getFirstHeader(LOCATION).getElements();
        String redirectedUrl = Arrays.stream(headerLocationElements).findFirst().get().getName();
        return redirectedUrl.substring(REDIRECT_URL_LENGTH);
    }

    private String getRequestGetDsspContext() throws IOException {
        chekDsspResultStatus();
        return getDsspResult();
    }

    private void chekDsspResultStatus() throws IOException {
        String status = "";
        HttpGet request = new HttpGet(format(DSSP_STATUS_URL, resultId));
        do {
            HttpResponse response = httpClient.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(jsonString);
            status = json.getString(STATUS_JSON_PARAM);
        } while (!SUCCESS_STATUS.equals(status));
    }

    private String getDsspResult() throws IOException {
        HttpGet request = new HttpGet(format(DSSP_RESULT_URL, resultId));
        HttpResponse response = httpClient.execute(request);
        String jsonString = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(jsonString);
        String fullResult = json.getString(RESULT_JSON_PARAM);
        return getResultWithoutHeader(fullResult);
    }

    private String getResultWithoutHeader(String fullResult) {
        int resNumIndex = fullResult.indexOf(RESNUM);
        return fullResult.substring(resNumIndex + START_DSSP_CONTEXT);
    }
}