"use strict";


async function createTable() {
    removeRows();
    let url = "/SQLTable_war/ServletJndi/list"
    let response = await fetch(url);
    let result = await response.json()
    let personArray = result;
    let tbody = document.getElementById("tbody");
    debugger
    for (let person of personArray) {
        let tr = document.createElement('tr');
        let td0 = document.createElement('td');
        td0.innerHTML = person.id;
        tr.appendChild(td0);
        let td1 = document.createElement('td');
        td1.innerHTML = person.firstName;
        tr.appendChild(td1);
        let td2 = document.createElement('td');
        td2.innerHTML = person.lastName;
        let td3 = document.createElement('td');
        let link = document.createElement('a');
        let link2 = document.createElement('a');
        link2.innerHTML = "edit";
        link2.href = `./ServletJndi/edit?id=${person.id}`
        link.innerHTML = "delete";
        link.href = `./ServletJndi/delete?id=${person.id}`
        td3.appendChild(link2);
        td3.appendChild(link);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tbody.appendChild(tr);
    }
}

function removeRows() {
    let tbody = document.getElementById("tbody");
    let length = tbody.childNodes.length
    for(let i=0;i<length;i++){
        tbody.removeChild(tbody.firstChild);
    }
}
