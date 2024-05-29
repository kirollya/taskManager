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
        date: document.getElementById("date").value,
        completed: false
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

async function updTaskCompleted(task_id) {
    response = await fetch("http://localhost:8080/task/updTaskCompleted/" + task_id, {
      method: "POST",
      body: JSON.stringify(document.getElementById(task_id + "_completed").checked),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    });
    if (response.ok) {
        document.getElementById(task_id + "_updTaskBtn").className = "deactivatedBtn";
        location.reload();
        return;
    }
}

function completedUpd(task_id) {
    if (document.getElementById(task_id + "_completed").checked.toString() == document.getElementById(task_id + "_completed").value) {
        document.getElementById(task_id + "_updTaskBtn").className = "deactivatedBtn";
    } else {
        document.getElementById(task_id + "_updTaskBtn").className = "activatedBtn";
    }
}

async function updTaskDate(task_id) {
    response = await fetch("http://localhost:8080/admin/task/updTaskDate/" + task_id, {
          method: "POST",
          body: JSON.stringify(document.getElementById(task_id + "_date").value),
          headers: {
            "Content-type": "application/json; charset=UTF-8"
          }
        });
        if (response.ok) {
            document.getElementById(task_id + "_updTaskBtn").className = "deactivatedBtn";
            document.getElementById(task_id + "_deleteTaskBtn").className = "activatedBtn";
            location.reload();
            return;
        }
}

function dateUpd(task_id) {
    document.getElementById(task_id + "_updTaskBtn").className = "activatedBtn";
    document.getElementById(task_id + "_deleteTaskBtn").className = "deactivatedBtn";
}