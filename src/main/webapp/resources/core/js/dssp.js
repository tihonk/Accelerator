let dropArea = document.getElementById('drop-area')
;['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, preventDefaults, false)
})
function preventDefaults (e) {
    e.preventDefault()
    e.stopPropagation()
}
;['dragenter', 'dragover'].forEach(eventName => {
    dropArea.addEventListener(eventName, highlight, false)
})
;['dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, unhighlight, false)
})
function highlight(e) {
    dropArea.classList.add('highlight')
}
function unhighlight(e) {
    dropArea.classList.remove('highlight')
}
dropArea.addEventListener('drop', handleDrop, false)
function handleDrop(e) {
    let dt = e.dataTransfer
    let files = dt.files
    handleFiles(files)
}
function handleFiles(files) {
    ([...files]).forEach(uploadFile)
}
function uploadFile(file) {
    var url = 'ВАШ URL ДЛЯ ЗАГРУЗКИ ФАЙЛОВ'
    var xhr = new XMLHttpRequest()
    var formData = new FormData()
    xhr.open('POST', url, true)
    xhr.addEventListener('readystatechange', function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Готово. Информируем пользователя
        }
        else if (xhr.readyState == 4 && xhr.status != 200) {
            // Ошибка. Информируем пользователя
        }
    })
    formData.append('file', file)
    xhr.send(formData)
}
let fileInput = document.getElementById('fileElem');
fileInput.addEventListener('change', function (e) {
    console.log(fileInput.files);
})
let textArea = document.getElementsByClassName('text-area')[0];
let radio2 = document.getElementById('b2');
function showFile(input) {
    let file = input.files[0];

    let reader = new FileReader();

    reader.readAsText(file);

    reader.onload = function() {
        textArea.value = reader.result;
        radio2.setAttribute('selected');
    };

    reader.onerror = function() {
        console.log(reader.error);
    };
}