async function find() {
    let additionalParams = "";
    worker = document.getElementById("workerId").value;
    chief = document.getElementById("chiefId").value;
    date = document.getElementById("dateInput").value;
    if (worker != "")
            additionalParams += "?workerId=" + worker;
    if (chief != "")
        if (additionalParams != "")
            additionalParams += "&chiefId=" + chief;
        else
            additionalParams += "?chiefId=" + chief;
    if (date != "")
        if (additionalParams != "")
            additionalParams += "&date=" + date;
        else
            additionalParams += "?date=" + date;
    window.location.replace("http://localhost:8080/task/getInfoWithFilter" + additionalParams);
}

async function makeReport() {
    params = window.location.href.split("?");
    additionalParams = params.length > 1 ? "?"+params[1] : ""
    window.location.href = ("http://localhost:8080/task/makeReport" + additionalParams);
}

async function assignEmployee(task_id) {
    chief = document.getElementById("chiefId").value;
    response = await fetch("http://localhost:8080/admin/task/" + task_id + "/assignWorker_" + chief, {
          method: "POST",
          headers: {
            "Content-type": "application/json; charset=UTF-8"
          }
        });
        if (response.ok) {
            location.reload();
            return;
        }
}