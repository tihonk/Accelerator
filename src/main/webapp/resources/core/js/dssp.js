let textArea = document.getElementsByClassName('text-area')[0];
function showFile(input) {
    let file = input.files[0];
    let reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function() {
        textArea.value = reader.result;
    };
    reader.onerror = function() {
        console.log(reader.error);
    };
}
