

const _v = {
	isValidItems : false,
	isValidPassword : false,
	isValidConfPassword : false,
	isValidEmail : false,
	emailPattern : /^[^\s@]+@[^\s@]+\.[^\s@]{2,4}$/
}

function formValidation(form){
	_v.form = document.querySelector(`${form}`);
	_v.passwordStrength = document.querySelectorAll(`#password > span`);
	_v.formItems = Array.from(_v.form.elements);	//memorizziamo tutti gli elementi del form
	_v.formMessaggePass = document.getElementById("messaggePass");
	_v.formMessaggeEmail = document.getElementById("messaggeEmail");
	
	checkPasswordStrength();
	submitForm();
}

//con questa funzione controlliamo che il form sia stato correttamente compilato
function submitForm(){
	  _v.form.addEventListener('submit', function (event) {
		event.preventDefault();
		event.stopPropagation();
		checkValidation();
	  }, false)
}

function checkValidation(){
	//controllo campi obbligatori
	requiredFields();
	
	//controllo validità email
	//isValidEmail();
	
	//checkPasswordStrength();
	
	//controllo validità password e corrispondenza con conferma
	checkPassword();
	
	//controlli
	
	finalCheck();
	
}

function requiredFields(){
	let error;
	//definisco un contatore in modo da verificare che tutti gli item siano true
	var count = 0;
	//_v.hasError = false;
	_v.formItems.forEach(item => {
		error = false;
		if(item.type !== 'checkbox' && item.required && item.value === ''){
			error = true;
		}
		if(item.type === 'checkbox' && item.required && !item.checked){
			error = true;	
		}
		if(error){
			//_v.hasError = true;
			item.classList.remove('is-valid');
			item.classList.add('is-invalid');
		}
		else{
			count ++;
			if(item.name=="password" || item.name=="email" || item.name=="passwordConf"){
				return
			}
			item.classList.remove('is-invalid');
			item.classList.add('is-valid');
		}
	});
	
	if(count == _v.formItems.length){
		_v.isValidItems = true;
	}
	else{
		_v.isValidItems = false;
	}
}


//VERIFICA VALIDITA' EMAIL; lo chiamo tramite onblur dall'input email
function isValidEmail(){
	if(_v.form.email.value == ""){
		_v.formMessaggeEmail.textContent = '';
		_v.formMessaggeEmail.className = "";
		_v.form.email.className ="form-control";
		_v.form.email.classList.remove('is-valid');
		_v.form.email.classList.remove('is-invalid');
	}
	else if(!_v.emailPattern.test(_v.form.email.value)){
		_v.formMessaggeEmail.textContent = 'L\' email non è valida';
		_v.formMessaggeEmail.className = "d-inline-flex mb-3 text-danger";
		_v.form.email.classList.remove('is-valid');
		_v.form.email.classList.add('is-invalid');
		
		_v.isValidEmail = false;
	}
	else{
		//richiesta Ajax per controllora se l'email esiste già nel DB.
		$.get("./emailController?email="+_v.form.email.value, 
			function(data){
				if(data == "esiste"){
					_v.isValidEmail = false;
					_v.formMessaggeEmail.textContent = 'L\' email inserita esiste';
					_v.formMessaggeEmail.className = "d-inline-flex mb-3 text-danger";
					_v.form.email.classList.remove('is-valid');
					_v.form.email.classList.add('is-invalid');
					
					console.log("isValidEmail() false ==> "+_v.formMessaggeEmail.textContent);
				}
				else{
					_v.formMessaggeEmail.textContent = '';
					_v.formMessaggeEmail.className = "";
					_v.form.email.className ="form-control";
					_v.form.email.classList.add('is-valid');
					_v.form.email.classList.remove('is-invalid');
					
					console.log("isValidEmail() true ==> "+_v.formMessaggeEmail.textContent);
					
					_v.isValidEmail = true;
				}
			}
		);
	}
}

//VERIFICA CONFERMA PASSWORD
function checkPassword(){
	const pwd = _v.form.password.value;
	const pwd_conf = _v.form.passwordConf.value;
		
	if(!_v.isValidPassword){
		_v.form.password.classList.add('is-invalid');
		_v.form.password.classList.remove('is-valid');
		_v.form.passwordConf.classList.add('is-invalid');
		_v.form.passwordConf.classList.remove('is-valid');
		_v.formMessaggePass.textContent = 'Inserire Password con minimo 8 caratteri,una lettera maiuscola e un numero';
		_v.formMessaggePass.className = " d-inline-flex p-2 text-danger";
		_v.isValidConfPassword = false;
	}
	else if(pwd!== pwd_conf){
		_v.form.passwordConf.classList.remove('is-valid');
		_v.form.passwordConf.classList.add('is-invalid');
		_v.formMessaggePass.textContent = "La Password confermata non coincide con quella inserita";
		_v.formMessaggePass.className = "d-inline-flex p-2 text-danger";
		_v.isValidConfPassword = false;
	}
	else if(pwd === pwd_conf){
		_v.form.passwordConf.classList.remove('is-invalid');
		_v.form.passwordConf.classList.add('is-valid');
		_v.formMessaggePass.textContent = "";
		_v.formMessaggePass.className = "";
		_v.isValidConfPassword = true;
	}
	else{
		_v.formMessaggePass.textContent = "";
		_v.formMessaggePass.className = "";
		_v.isValidConfPassword = false;
	}
}

function finalCheck(){
	//verifico se le variabili di validazione sono true; se è così invio il form al server
	if(_v.isValidPassword && _v.isValidConfPassword && _v.isValidEmail && _v.isValidItems){
		document.formRegistrazione.submit();
		console.log("True validate() ==> validPass: "+_v.isValidPassword+" validConfPass: "+_v.isValidConfPassword+" validEmail: "+_v.isValidEmail+" validItems: "+_v.isValidItems);
		
		return true;
	}
	console.log("False validate() ==> validPass: "+_v.isValidPassword+" validConfPass: "+_v.isValidConfPassword+" validEmail: "+_v.isValidEmail+" validItems: "+_v.isValidItems);
	return false;
}

/* VERIFICA PASSWORD
* 8 caratteri + 1 lettere maiusco + 1 numero -> valida ma non sicura
* 12 caratteri + 1 lettere maiusco + 1 numero -> mediamente sicura
* 16 caratteri + 1 lettere maiusco + 1 numero -> molto sicura
*/
function checkPasswordStrength(){
	_v.form.password.addEventListener('keyup', (e) => {
		const isValid = {
			isLow : false,
			isHigh : false
		};
		const pwd = e.target.value;
		
		if(pwd.length >= 8 && pwd.length < 16 && regexCount(/[A-Z]/g, pwd)>=1 && regexCount(/[0-9]/g, pwd)>=1 ){
			_v.form.password.classList.remove('is-invalid');
			_v.form.password.classList.add('is-valid');
			_v.passwordStrength[0].classList.add('active');
			_v.passwordStrength[1].classList.remove('active');
			_v.passwordStrength[2].classList.remove('active');
			
			if(pwd.length >= 12){
				_v.passwordStrength[1].classList.add('active');
				_v.passwordStrength[2].classList.remove('active');
			}
			isValid.isLow = true;
		}
		else if(pwd.length >= 16 && regexCount(/[A-Z]/g, pwd)>=1 && regexCount(/[0-9]/g, pwd)>=1){
			_v.form.password.classList.remove('is-invalid');
			_v.form.password.classList.add('is-valid');
			_v.passwordStrength[0].classList.add('active');
			_v.passwordStrength[1].classList.add('active');
			_v.passwordStrength[2].classList.add('active');
			isValid.isHigh = true;
		}
		else{
			_v.form.password.classList.remove('is-valid');
			_v.form.password.classList.add('is-invalid');
			_v.passwordStrength[0].classList.remove('active');
			_v.passwordStrength[1].classList.remove('active');
			_v.passwordStrength[2].classList.remove('active');
			
			isValid.isLow = false;
			isValid.isHigh = false;
		}
		_v.isValidPassword = (isValid.isLow || isValid.isHigh) ? true : false;
	});
}

//con questa funzione verifico se la password effettua dei match con il pattern
function regexCount(pattern, password){
	return (password.match(pattern) || []).length;
}



