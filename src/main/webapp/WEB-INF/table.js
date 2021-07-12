"use strict";

async function createTable() {
    let url = "/ServletJndi/list"
    let response = await fetch(url);
    let result = await response.json()
    let personArray = result;
    let tbody = document.getElementById("tbody");
    for (let person of personArray) {
        let tr = document.createElement('tr');
        let td0 = document.createElement('td');
        td0.innerHTML = person.id;
        tbody.appendChild(td0);
        let td1 = document.createElement('td');
        td1.innerHTML = person.firstName;
        tbody.appendChild(td1);
        let td2 = document.createElement('td');
        td2.innerHTML=person.lastName;
        let td3=document.createElement('td');
        let link = document.createElement('a');
        let link2 = document.createElement('a');
        link2.innerHTML = "Edit";
        link2.href=`./ServletJndi/edit?id=${person.id}`
        link.innerHTML = "Delete";
        link.href=`./ServletJndi/delete?id=${person.id}`
        td3.appendChild(link2);
        td3.appendChild(link);
        tbody.appendChild(td2);
        tbody.appendChild(td3);
        tbody.appendChild(tr);
    }
}
let btn = document.querySelector("button");
btn.addEventListener("click",()=>{
    document.location.href=`./person-list.jsp`
})


createTable()