getUser(userUrl);
getUserTable(adminUrl);


async function getUser(url) {
    let response = await fetch(url);
    let user = await response.json();

    let paragraphUser = document.querySelector('#headerUsername');
    paragraphUser.innerHTML = user.username;
    let paragraphRoles = document.querySelector('#headerRoles');
    let tempRoles = "";
    for (let i = 0; i < user.roles.length; i++) {
        tempRoles = tempRoles + " " + user.roles[i].role.toString().replace("ROLE_", "");
        paragraphRoles.innerHTML = tempRoles;
    }
}


async function getUserTable(url) {
    let response = await fetch(url);
    let users = await response.json();

    let captions = ["id", "username", "surname", "age", "email", "roles"];

    let tbody = document.querySelector('#mainTable tbody');


    for (let i = 0; i < users.length; i++) {

        let tr = document.createElement('tr');

        for (let j = 0; j < captions.length; j++) {
            let td = document.createElement('td');
            let paragraph = document.createElement('p');

            if (j == captions.length - 1) {
                let roles = "";
                for (let k = 0; k < users[i].roles.length; k++) {
                    roles = roles + " " + users[i].roles[k].role.toString().replace("ROLE_", "");
                    paragraph.innerHTML = roles;
                }
            } else {
                paragraph.innerHTML = users[i][captions[j]];
            }
            td.appendChild(paragraph);
            tr.appendChild(td);
        }
        let editButton = document.createElement('a');
        editButton.setAttribute("href", "api/findUser/" + users[i].id);
        editButton.setAttribute("class", "btn btn-primary eBtn");
        editButton.setAttribute("data-bs-toggle", "modal");
        editButton.setAttribute("data-bs-target", "#exampleModal");
        editButton.innerHTML = "Edit";
        let td = document.createElement('td');
        td.appendChild(editButton);
        tr.appendChild(td);
        let deleteButton = document.createElement('a');
        deleteButton.setAttribute("href", "api/findUser/" + users[i].id);
        deleteButton.setAttribute("class", "btn btn-primary btn-danger deleteBtn");
        deleteButton.setAttribute("data-bs-toggle", "modal");
        deleteButton.setAttribute("data-bs-target", "#exampleModalDelete");
        deleteButton.innerHTML = "Delete";
        let td2 = document.createElement('td');
        td2.appendChild(deleteButton);
        tr.appendChild(td2);
        tbody.appendChild(tr);
    }
}



