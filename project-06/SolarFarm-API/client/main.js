// console.log("Hello from the js file")
const baseUrl = "http://localhost:8080/api/solarpanel"
const panels = document.getElementById("panels");

function getAllPanels(){
fetch(baseUrl)
.then (response => {
    if(response.status === 200){
        return response.json();
    } else {
        console.log(response);
    }
})
.then (listOfPanels => renderPanels(listOfPanels))
.catch (errorMessage => console.log(errorMessage));
}

function renderPanels(panels){
    const panelRows = panels.map(p =>`<tr>
            <td>${p.id}</td>
            <td>${p.section}</td>
            <td>${p.row}</td>
            <td>${p.column}</td>
            <td>${p.yearInstalled}</td>
            <td>${p.material}</td>
            <td>${p.tracking}</td>
            <td><button onClick="editPanel(${p.id})">Edit</button><buttononClick="deletePanel(${p.id})>Delete</button>
        </tr>`);
        //combine all
        const html = panelRows.reduce((prev, curr) => prev + curr)
        document.querySelector("#panels tbody").innerHTML = html;
};

function addPanel(evt){
    evt.preventDefault();
    
    const section = document.getElementById("section").value;
    const row = document.getElementById("row").value;
    const column = document.getElementById("col").value;
    const yearInstalled = document.getElementById("yearInstalled").value;
    const material = document.getElementById("material").value;
    const tracking = document.getElementById("tracking").checked;
    
    const paneltoAdd = {section, row, column, yearInstalled, material, tracking};

    fetch(baseUrl, {
        method: "POST",
        body: JSON.stringify(paneltoAdd),
        headers: {
            "Content Type" : "application/json"
        }
    })
    .then (response => {
        if(response.status === 200){
            return response.json();
        }
            console.log(response);
})
    .then (addedPanel => getAllPanels)
    .catch (errorMessage => console.log(errorMessage));
}

getAllPanels();

