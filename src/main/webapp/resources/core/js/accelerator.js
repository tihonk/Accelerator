let periodicTableController;
let $selectedElement = document.querySelector('#selectedElement');
let $selectedElementWrapper = document.querySelector('.selectedElement');
{
    let periodicTableHTML = `
                    <div>
                        <div class="s">
                            <div>H</div>
                            <div>Li</div>
                            <div>Na</div>
                            <div>K</div>
                            <div>Rb</div>
                            <div>Cs</div>
                            <div>Fr</div>
                        </div>
                        <div class="s">
                            <div>Be</div>
                            <div>Mg</div>
                            <div>Ca</div>
                            <div>Sr</div>
                            <div>Ba</div>
                            <div>Ra</div>
                        </div>
                        <div class="d">
                            <div>Sc</div>
                            <div>Y</div>
                            <div>-</div>
                            <div>-</div>
                        </div>
                        <div class="d">
                            <div>Ti</div>
                            <div>Zr</div>
                            <div>Hf</div>
                            <div>Rf</div>
                        </div>
                        <div class="d">
                            <div>V</div>
                            <div>Nb</div>
                            <div>Ta</div>
                            <div>Db</div>
                        </div>
                        <div class="d">
                            <div>Cr</div>
                            <div>Mo</div>
                            <div>W</div>
                            <div>Sg</div>
                        </div>
                        <div class="d">
                            <div>Mn</div>
                            <div>Tc</div>
                            <div>Re</div>
                            <div>Bh</div>
                        </div>
                        <div class="d">
                            <div>Fe</div>
                            <div>Ru</div>
                            <div>Os</div>
                            <div>Hs</div>
                        </div>
                        <div class="d">
                            <div>Co</div>
                            <div>Rh</div>
                            <div>Ir</div>
                            <div>Mt</div>
                        </div>
                        <div class="d">
                            <div>Ni</div>
                            <div>Pd</div>
                            <div>Pt</div>
                            <div>Ds</div>
                        </div>
                        <div class="d">
                            <div>Cu</div>
                            <div>Ag</div>
                            <div>Au</div>
                            <div>Rg</div>
                        </div>
                        <div class="d">
                            <div>Zn</div>
                            <div>Cd</div>
                            <div>Hg</div>
                            <div>Cn</div>
                        </div>
                        <div class="p">
                            <div>B</div>
                            <div>Al</div>
                            <div>Ga</div>
                            <div>In</div>
                            <div>Ti</div>
                            <div>Uut</div>
                        </div>
                        <div class="p">
                            <div>C</div>
                            <div>Si</div>
                            <div>Ge</div>
                            <div>Sn</div>
                            <div>Rb</div>
                            <div>Fl</div>
                        </div>
                        <div class="p">
                            <div>N</div>
                            <div>P</div>
                            <div>As</div>
                            <div>Sb</div>
                            <div>Bi</div>
                            <div>Uup</div>
                        </div>
                        <div class="p">
                            <div>O</div>
                            <div>S</div>
                            <div>Se</div>
                            <div>Te</div>
                            <div>Po</div>
                            <div>Lv</div>
                        </div>
                        <div class="p">
                            <div>F</div>
                            <div>Cl</div>
                            <div>Br</div>
                            <div>I</div>
                            <div>At</div>
                            <div>Uus</div>
                        </div>
                        <div class="p">
                            <div>He</div>
                            <div>Ne</div>
                            <div>Ar</div>
                            <div>Kr</div>
                            <div>Xe</div>
                            <div>Rn</div>
                            <div>Uuo</div>
                        </div>
                    </div>
                    <div class="f">
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                        <div>
                            <div>La</div>
                            <div>Ac</div>
                        </div>
                    </div>`;
    let $shade = document.createElement('div');
    $shade.classList.add('shade');
    $periodicTable = document.createElement('section');
    $periodicTable.id = "periodic_table";
    $periodicTable.innerHTML = periodicTableHTML;
    document.body.appendChild($periodicTable);
    document.body.appendChild($shade);
    let periodicStyle = $periodicTable.style;
    let shadeStyle = $shade.style;
    let hiding = false;
    periodicTableController = {
        show: () => {
            if (hiding) return;
            hiding = true;
            periodicStyle.display = 'block';
            shadeStyle.display = 'block';
            setTimeout(() => {
                periodicStyle.opacity = '1';
                shadeStyle.opacity = '0.5';
                setTimeout(() => hiding = false)
            });
        },
        hide: () => {
            hiding = true;
            periodicStyle.opacity = '0';
            shadeStyle.opacity = '0';
            setTimeout(() => {
                periodicStyle.display = 'none';
                shadeStyle.display = 'none';
                hiding = false;
            }, 1000);
        }
    }
    $periodicTable.addEventListener('click', ev => {
        if (!(ev.target.innerText.length === 1 || ev.target.innerText.length === 2 || ev.target.innerText.length === 3)) return;
        $selectedElement.innerHTML = ev.target.innerText;
        console.log('click');
        periodicTableController.hide();
    });
    $shade.addEventListener('click', () => {
        periodicTableController.hide();
    });
}

$selectedElementWrapper.addEventListener('click', () => {
    periodicTableController.show();
});

