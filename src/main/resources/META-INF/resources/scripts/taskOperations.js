async function deleteTask(id) {
    response = await fetch("http://localhost:8080/admin/task/delete/" + id, {
      method: "POST",
      body: JSON.stringify({}),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    });
    if (response.ok) {
        location.reload();
        return;
    }
}

async function addTask(currentUser) {
    response = await fetch(
        "http://localhost:8080/admin/employee/get/" + currentUser, {
        method: "GET"
        }
    )
    employee = await response.json()
    response = await fetch("http://localhost:8080/admin/task/add", {
      method: "POST",
      body: JSON.stringify({
        chief: employee,
        content: document.getElementById("content").value,
        date: document.getElementById("date").value
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    });

    if (response.ok) {
        location.reload();
        return;
    }
}