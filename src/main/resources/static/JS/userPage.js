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

getUser(userUrl);

async function getUserTable(url) {
    let response = await fetch(url);
    let user = await response.json();


    let captions = ["id", "username", "surname", "age", "email", "roles"];

    let tbody = document.querySelector('#mainTable tbody');
    let tr = document.createElement('tr');

    for (let i = 0; i < captions.length; i++) {
        let td = document.createElement('td');
        let paragraph = document.createElement('p');

        if (i == captions.length - 1) {
            let roles = "";
            for (let i = 0; i < user.roles.length; i++) {
                roles = roles + " " + user.roles[i].role.toString().replace("ROLE_", "");
                paragraph.innerHTML = roles;
            }
        } else {
            paragraph.innerHTML = user[captions[i]];
        }
        td.appendChild(paragraph);
        tr.appendChild(td);
    }
    tbody.appendChild(tr);
}

getUserTable(userUrl);
