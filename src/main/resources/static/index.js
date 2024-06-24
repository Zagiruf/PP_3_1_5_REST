const URL = 'http://localhost:8080/api/users';
const tbody = $('#AllUsers');
const tbodyPrincipal = $('#userPrincipal');
navbar();

function navbar() {
    fetch('/api/userPrincipal')
        .then(res => res.json())
        .then(user => {
            const roles = user.roles.map(role => role.name).join(',')
            $('#header-text-info-user').text(user.username)
            $('#navbar-role').text(roles)

        })
}


getTableUser();

function getTableUser() {
    tbody.empty();
    fetch(URL)
        .then(res => res.json())
        .then(js => {
            js.forEach(user => {
                const roles = user.roles.map(role => role.name).join(', ');
                const users = $(
                    `<tr>
                         <td class="pt-3" >${user.id}</td>
                        <td class="pt-3" >${user.username}</td>
                        <td class="pt-3" >${user.age}</td>
                        <td class="pt-3" >${roles}</td>
                        <td>
                            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModal" onclick="editModal(${user.id})">
                            Edit
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#exampleModalDel" onclick="deleteModal(${user.id})">
                                Delete
                            </button>
                        </td>
                    </tr>`
                );
                tbody.append(users);
            });
        })
}


async function getOneUser(id) {
    let response = await fetch("/api/user/" + id);
    return await response.json();
}

async function openAndFillInTheEditModal(form, modal, id) {
    modal.show();
    let user = await getOneUser(id);

    // Заполнение полей формы
    document.getElementById('Edit_id').value = user.id;
    document.getElementById('username').value = user.username;
    document.getElementById('age').value = user.age;
    document.getElementById('password').value = user.password;

    // Заполнение ролей
    const rolesSelect = document.getElementById('allRoles');
    for (let option of rolesSelect.options) {
        option.selected = user.roles.some(role => role.id == option.value);
    }
}

const formEdit = document.forms["formEdit"];
editUser()

async function editModal(id) {
    const modalElement = document.querySelector('#exampleModal');
    const modal = new bootstrap.Modal(modalElement);
    await openAndFillInTheEditModal(formEdit, modal, id);
}

function editUser() {


    formEdit.addEventListener("submit", ev => {
        ev.preventDefault();

        let roles = [];
        for (let i = 0; i < formEdit.roles.options.length; i++) {
            if (formEdit.roles.options[i].selected) {
                roles.push({id: formEdit.roles.options[i].value}); // Сохраняем объект с id роли
            }
        }

        const userData = {
            id: formEdit.id.value,
            username: formEdit.username.value,
            age: formEdit.age.value,
            password: formEdit.password.value,
            roles: roles
        };


        fetch('api/user/' + formEdit.id.value, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                $('#closeEdit').click();
                getTableUser();
            })
    });
}

let deleteForm = document.forms["formDelete"];
deleteUser();

async function deleteModal(id) {
    const modalElement = document.querySelector('#exampleModalDel');
    const modal = new bootstrap.Modal(modalElement);
    await openAndFillInTheDeleteModal(deleteForm, modal, id);
}

function deleteUser() {
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("api/user/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#closeDelete').click();
            getTableUser();
        });
    });
}


async function openAndFillInTheDeleteModal(form, modal, id) {
    modal.show();
    let user = await getOneUser(id);

    // Заполнение полей формы для удаления
    document.getElementById('Delete_id').value = user.id;
    document.getElementById('usernameDel').value = user.username;
    document.getElementById('ageDel').value = user.age;
    document.getElementById('passwordDel').value = user.password;

    // Заполнение ролей
    const rolesSelect = document.getElementById('allRolesDel');
    for (let option of rolesSelect.options) {
        option.selected = user.roles.some(role => role.id == option.value);
    }
}


let form = document.forms["create"];
createNewUser()

function createNewUser() {
    form.addEventListener("submit", ev => {
        ev.preventDefault();
        let roles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) roles.push({
                id: form.roles.options[i].value,
                name: "ROLE_" + form.roles.options[i].text
            });
        }
        fetch("api/user", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: form.username.value,
                age: form.age.value,
                password: form.password.value,
                roles: roles
            })
        }).then(() => {
            form.reset();
            $('#home-tab').click();
            getTableUser();
        });
    });
}

getPrincipalUser()

function getPrincipalUser() {
    tbodyPrincipal.empty();
    fetch('/api/userPrincipal')
        .then(res => res.json())
        .then(userPrincipal => {
            const roles = userPrincipal.roles.map(role => role.name).join(', ');
            const users = $(
                `<tr>
                        <td>${userPrincipal.id}</td>
                        <td>${userPrincipal.username}</td>
                        <td>${userPrincipal.age}</td>
                        <td>${roles}</td>
                    </tr>`
            );
            tbodyPrincipal.append(users);
        });

}

