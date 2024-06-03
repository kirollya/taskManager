async function deleteEmployee(employeeId) {
    response = await fetch("http://localhost:8080/admin/employee/delete/" + employeeId, {
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
    alert("Невозможно удалить данного сотрудника, потому что на него ссылаются некоторые задачи");
}

async function addEmployee() {
    response = await fetch("http://localhost:8080/admin/employee/add", {
      method: "POST",
      body: JSON.stringify({
        username: document.getElementById("employeeLogin").value,
        fio: document.getElementById("employeeName").value,
        post: document.getElementById("employeePost").value,
        rank: document.getElementById("employeeRank").value,
        password: null,
        role: null
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

function rankChanged(employee_id) {
    document.getElementById(employee_id + "_updEmployeeBtn").className = "activatedBtn";
    document.getElementById(employee_id + "_deleteEmployeeBtn").className = "deactivatedBtn";
}

async function updEmployeeRank(employee_id) {
    response = await fetch("http://localhost:8080/admin/employee/updEmployeeRank/" + employee_id, {
      method: "POST",
      body: JSON.stringify(document.getElementById(employee_id + "_rank").value),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    });
    if (response.ok) {
        document.getElementById(employee_id + "_updEmployeeBtn").className = "deactivatedBtn";
        document.getElementById(employee_id + "_deleteEmployeeBtn").className = "activatedBtn";
        location.reload();
        return;
    }
}