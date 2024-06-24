const tbodyP = $('#tbodyUser');
navbar();

function navbar() {
    fetch("/api/userPrincipal")
        .then(res => res.json())
        .then(user => {
            const roles = user.roles.map(role => role.name).join(',')
            $('#usernameNav').text(user.username)
            $('#rolesNav').text(roles)
        })
}


getUser()

function getUser() {
    tbodyP.empty();
    fetch("api/userPrincipal")
        .then(res => res.json())
        .then(userP => {
            const roles = userP.roles.map(role => role.name).join(', ');
            const users = $(
                `<tr>
                        <td>${userP.id}</td>
                        <td>${userP.username}</td>
                        <td>${userP.age}</td>
                        <td>${roles}</td>
                    </tr>`
            );
            tbodyP.append(users);
        });

}
