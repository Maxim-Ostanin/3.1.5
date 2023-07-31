document.addEventListener('DOMContentLoaded', () => {
    window.onload = function () {
        let tbody = document.querySelector('#mainTable tbody')

        const adminRole = {
            id: 1,
            users: null,
            authority: "ROLE_ADMIN",
            role: "ROLE_ADMIN"
        }

        const userRole = {
            id: 2,
            users: null,
            authority: "ROLE_USER",
            role: "ROLE_USER"
        }

        let createUsername = document.getElementById('username');
        let createSurname = document.getElementById('surname');
        let createAge = document.getElementById('age');
        let createEmail = document.getElementById('email');
        let createPassword = document.getElementById('password');
        let createRoles = document.getElementById('roles');

        let editId = document.getElementById('id');
        let editUsername = document.getElementById('editusername');
        let editSurname = document.getElementById('editsurname');
        let editAge = document.getElementById('editage');
        let editEmail = document.getElementById('editemail');
        let editPassword = document.getElementById('editpassword');
        let editRoles = document.getElementById('editroles');


        let deleteId = document.getElementById('deleteid');
        let deleteUsername = document.getElementById('deleteusername');
        let deleteSurname = document.getElementById('deletesurname');
        let deleteAge = document.getElementById('deleteage');
        let deleteEmail = document.getElementById('deleteemail');
        let deleteRoles = document.getElementById('deleteroles');

        const deleteUrl = "/api/admin/"

        const editForm = document.getElementById('editForm');
        const deleteForm = document.getElementById('deleteForm');
        const createForm = document.getElementById('createForm');


        let formID = 0;

        const on = (element, event, selector, handler) => {
            element.addEventListener(event, e => {
                if (e.target.closest(selector)) {
                    handler(e)
                }
            })
        }

///Delete button
        let tableRow;
        on(document, 'click', '.deleteBtn', e => {
            const fila = e.target.parentNode.parentNode
            tableRow = e.target.parentNode.parentNode
            const id = fila.children[0].children[0].innerHTML
            formID = fila.children[0].children[0].innerHTML;

            const username = fila.children[1].children[0].innerHTML
            const surname = fila.children[2].children[0].innerHTML
            const age = fila.children[3].children[0].innerHTML
            const email = fila.children[2].children[0].innerHTML
            const roles = fila.children[5].children[0].innerHTML

            deleteId.value = id
            deleteUsername.value = username
            deleteSurname.value = surname
            deleteAge.value = age
            deleteEmail.value = email
            deleteRoles.value = roles

            $('.myForm #exampleModalDelete').showModal();


        })

////Delete form

        deleteForm.addEventListener('submit', (e) => {
            e.preventDefault()
            let modalBody = document.querySelector("body")
            fetch(deleteUrl + formID, {
                method: 'DELETE'
            })
                .then(() => tableRow.parentNode.removeChild(tableRow))
                .then(() => modalBody.setAttribute("class", " "))
                .then(() => modalBody.setAttribute("style", " "))

            $('.modal-backdrop').hide();
            $('.myForm #exampleModalDelete').hide();
        })

///Edit button

        on(document, 'click', '.eBtn', e => {
            const fila = e.target.parentNode.parentNode
            tableRow = e.target.parentNode.parentNode

            const id = fila.children[0].children[0].innerHTML
            formID = fila.children[0].children[0].innerHTML;

            const username = fila.children[1].children[0].innerHTML
            const surname = fila.children[2].children[0].innerHTML
            const age = fila.children[3].children[0].innerHTML
            const email = fila.children[2].children[0].innerHTML
            const password = fila.children[4].children[0].innerHTML
            const roles = fila.children[5].children[0].innerHTML

            editId.value = id
            editUsername.value = username
            editSurname.value = surname
            editAge.value = age
            editEmail.value = email
            editPassword.value = password
            editRoles.options.select = roles
            $('.myForm #exampleModal').show();


        })

        const changeRow = (user) => {
            let stringRoles = "";
            for (let i = 0; i < user.roles.length; i++) {
                stringRoles += user.roles[i].role.replace("ROLE_", "") + " "
            }

            tableRow.children[0].children[0].innerHTML = user.id
            tableRow.children[1].children[0].innerHTML = user.username
            tableRow.children[2].children[0].innerHTML = user.surname
            tableRow.children[3].children[0].innerHTML = user.age
            tableRow.children[2].children[0].innerHTML = user.email
            tableRow.children[4].children[0].innerHTML = user.password
            tableRow.children[5].children[0].innerHTML = stringRoles

        }

////Edit form

        editForm.addEventListener('submit', (e) => {
            e.preventDefault()
            let formData = new FormData(editForm)
            let roles = $('[id="editroles"]').val();

            for (let i = 0; i < roles.length; i++) {

                if (roles[i] === userRole.role) {
                    roles[i] = userRole
                }
                if (roles[i] === adminRole.role) {
                    roles[i] = adminRole
                }
            }

            var object = {
                ...Object.fromEntries(formData), "roles": roles
            };
            console.log(JSON.stringify(object))
            fetch(deleteUrl + formID, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(object)
            })
                .then(response => response.json())
                .then(user => changeRow(user))
            // .then(response => location.reload())
            $('.modal-backdrop').hide();
            $('.myForm #exampleModal').hide();
        })

/// Add new user row at the table

        const addUser = (user) => {
            console.log(user)
            let stringRoles = "";
            for (let i = 0; i < user.roles.length; i++) {
                stringRoles += user.roles[i].role.replace("ROLE_", "") + " "
            }


            let newRow = `<tr>
                                    <td><p>${user.id}</p></td>
                                    <td><p>${user.username}</p></td>
                                    <td><p>${user.surname}</p></td>
                                    <td><p>${user.age}</p></td>
                                    <td><p>${user.email}</p></td>
                                    <td><p>${stringRoles}</p></td>
                                    <td><a href="api/findUser/${user.id}" class="btn btn-primary eBtn" data-bs-toggle="modal" data-bs-target="#exampleModal">Edit</a></td>
                                    <td><a href="api/findUser/${user.id}" class="btn btn-primary btn-danger deleteBtn" data-bs-toggle="modal" data-bs-target="#exampleModalDelete">Delete</a></td>
                                    </tr>`
            tbody.innerHTML += newRow;
        }

/// Create form

        createForm.addEventListener('submit', (e) => {
            e.preventDefault()
            let formData = new FormData(createForm)
            let roles = $('[id="roles"]').val();

            for (let i = 0; i < roles.length; i++) {

                if (roles[i] === userRole.role) {
                    roles[i] = userRole
                }
                if (roles[i] === adminRole.role) {
                    roles[i] = adminRole
                }
            }

            var object = {
                ...Object.fromEntries(formData), "roles": roles
            };
            fetch(deleteUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(object)
            })
                .then(response => response.json())
                .then(user => addUser(user))
                .then(() => switchTab())
            // .then(response => location.reload())
        })

//// Create form button

        function switchTab() {
            let mainTab = document.getElementById('home-tab-pane')
            let mainTabInner = document.getElementById('home-tab')
            let newUserTab = document.getElementById('newUser-tab-pane')
            let newUserTabInner = document.getElementById('newUser-tab')
            mainTab.setAttribute("class", "tab-pane fade show active")
            newUserTab.setAttribute("class", "tab-pane fade")
            mainTabInner.setAttribute("class", "nav-link active")
            newUserTabInner.setAttribute("class", "nav-link")

            createUsername.value = ''
            createSurname.value = ''
            createAge.value = ''
            createEmail.value = ''
            createPassword.value = ''
            createRoles.value = ''

        }
    }
});