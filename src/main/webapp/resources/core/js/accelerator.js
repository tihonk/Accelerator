let selectElementInput = document.getElementById('selectElement-input');
let selectedElement = document.getElementsByClassName('selectedElement')[0];
let elements = document.querySelectorAll('#periodic_table > div > div > div');
elements.forEach(e => e.addEventListener('click', function () {
    selectElementInput.click();
    if(this.innerText!='-') {
        selectedElement.value = this.innerText;
    }
}));