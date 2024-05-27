var btn = document.getElementById("saveEmployeeBtn");

var employeeLoginId = "employeeLogin";
var employeeFioId = "employeeFio";
var employeePostId = "employeePost";
var employeeRankId = "employeeRank";
var employeeLogin = document.getElementById(employeeLoginId);
var employeeFio = document.getElementById(employeeFioId);
var employeePost = document.getElementById(employeePostId);
var employeeRank = document.getElementById(employeeRankId);

btn.onclick = $http.post('/employee/add', '{"fio": "' + employeeFio + '","post": "' + employeePost + '","rank": ' + employeeRank + '","username": ' + employeeLogin + '}')