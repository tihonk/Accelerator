let textArea = document.querySelector('#textarea');
let droppedFileName = document.querySelector('.dropped-file-name');

function showFile(file) {
    if (file.size > 2_000_000) {
        fileTooBig();
        return;
    }
    console.log(file);
    let reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function () {
        resultHeader__fileName.innerText = file.name;
        textArea.value = reader.result;
    };
    reader.onerror = function () {
        console.log(reader.error);
    };
}

function clearForm() {
    clearForm.clearBtn.click();
}

clearForm.clearBtn = document.querySelector('#clearForm');

document.addEventListener('DOMContentLoaded', function () {
    console.log('dom loaded')
    let tabs = document.querySelector('.tabs');
    console.log(tabs)
    let instance = M.Tabs.init(tabs, {
        swipeable: true,
        duration: '300ms',
        color: 'white'
    });
    instance.select('tab-1');
});

let dropArea = document.querySelector('#dropArea');
let resultHeader__fileName = document.querySelector('#result-header__fileName');
dropArea.addEventListener('drop', e => {
    clearForm();
    let file = e.dataTransfer.files[0];
    droppedFileName.innerHTML = file.name;
    droppedFileName.style.display = 'block';
    showFile(file);
});

['dragenter', 'dragover', 'dragleave', 'drop'].forEach(ev => {
    document.body.addEventListener(ev, e => {
        e.preventDefault();
    })
})

dropArea.addEventListener('dragenter', () => {
    dropArea.classList.add('highlight');
})

dropArea.addEventListener('dragleave', () => {
    dropArea.classList.remove('highlight');
})

let dsspResultWindow = document.querySelector('.result-container');

let dsspResultContent = dsspResultWindow.querySelector('.result-content.textarea');

dsspResultWindow.hide = function () {
    this.classList.remove('visible');
    setTimeout(() => {
        this.style.display = 'none';
    }, 100);
}

dsspResultWindow.show = function () {
    this.style.display = 'block';
    setTimeout(() => {
        this.classList.add('visible');
    }, 100);
}

document.querySelector('.close-result').addEventListener('click', () => {
    dsspResultWindow.hide();
})

function getDsspResult() {
    // Послать на сервер value из textarea
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'https://jsonplaceholder.typicode.com/posts', true);
    xhr.send();
    xhr.onload = function () {
        dsspResultWindow.show();
        dsspResultContent.innerHTML = xhr.response;
    };

    xhr.onerror = function () {

    };

    xhr.onprogress = function (event) {
    };
}
