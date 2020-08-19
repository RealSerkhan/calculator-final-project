const check = function () {
    if (document.getElementById('password').value ===
        document.getElementById('confirm_password').value) {
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = 'matching';
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'not matching';
    }
};

function checkPass(){
    const pass = document.getElementById("password").value;
    const rpass = document.getElementById("confirm_password").value;
    if(pass !== rpass){
        document.getElementById("submit").disabled = true;
        $('.missmatch').html("Entered Password is not matching!! Try Again");
    }else{
        $('.missmatch').html("");
        document.getElementById("submit").disabled = false;
    }
}

function onChange() {
    const password = document.querySelector('input[name=password]');
    const confirm = document.querySelector('input[name=confirm_password]');
    if (confirm.value === password.value) {
        confirm.setCustomValidity('');
    } else {
        confirm.setCustomValidity('Passwords do not match');
    }
}