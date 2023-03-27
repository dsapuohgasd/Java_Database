<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %><%
  User authUser = (User) request.getAttribute( "AuthUser" ) ;
  String home = request.getContextPath() ;
%>
<div class="user-profile">
  <h1>Кабинет пользователя</h1>
  <img class="profile-avatar"
       src="<%=home%>/image/<%=authUser.getAvatar()%>"
       alt="<%=authUser.getLogin()%>" />

  <fieldset class="profile-fieldset">
    <legend>Возможно для изменения</legend>
    <p class="profile-name">
      <span>Name:</span> <b data-field-name="name"><%= authUser.getName() %></b>
    </p>
    <p class="profile-name">
      <span>Login:</span> <b data-field-name="login"><%= authUser.getLogin() %></b>
    </p>
    <p class="profile-name">
      <span>E-mail:</span> <b data-field-name="email"><%= authUser.getEmail() %></b>
      <%if(authUser.getEmailCode()!=null){%>
        <a href="<%=home%>/checkmail/"> <img class="galochka" src="<%=home%>/img/pngegg (1).png" alt="">
          Подтвердить почту</a>
      <%}else{%>
      <img class="galochka" src="<%=home%>/img/pngegg.png" alt="">
      <%}%>
    </p>
    <p class="profile-fieldset-avatar">
      <span>Картинка:</span>
      <input type="file" id="avatar-input" alt="avatar-input">
      <input type="button" value="Save" id="avatar-save-button">
    </p>
    <p style="border: 1px solid steelblue; margin: 3px; padding: 3px">
      Пароль: <label><input type="password"></label><br>
      Повтор: <label><input type="password"></label>
      <input type="button" value="Установить" id="change-pass-button">
    </p>
  </fieldset>
</div>
<script>
  document.addEventListener( "DOMContentLoaded", () => {
    const avatarSave = document.querySelector( "#avatar-save-button" ) ;
    const changePassButton = document.querySelector("#change-pass-button");
    if(!changePassButton) throw "'#change-pass-button' not found";
    changePassButton.addEventListener('click', changePassClick);
    if( ! avatarSave ) throw "#avatar-save-button not found" ;
    avatarSave.addEventListener('click',avatarSaveClick);
    for(let nameElement of document.querySelectorAll(".profile-name b")){
      nameElement.addEventListener( "click", nameClick ) ;
      nameElement.addEventListener( "blur", nameBlur ) ;
      nameElement.addEventListener( "keydown", nameKeydown ) ;
    }
  });
  function changePassClick(e){
    let passwords = e.target.parentNode.querySelectorAll('input[type="password"]');
    fetch("/Webbasics_war_exploded/register/?password=" + passwords[0].value, {
      method:"PUT",
      headers: {},
      body: ""
    }).then(r => r.text())
            .then(t => {
              console.log(t);
              passwords[0].value = passwords[1].value = '';
              alert("Password was changed");
            });
  }
  function avatarSaveClick(){
    const avatarInput = document.querySelector("#avatar-input");
    if(!avatarInput)throw "#avatar-input not found" ;
    if(avatarInput.files.length===0){
      alert("slelect a file");
      return
    }
    let formData = new FormData();
    formData.append("userAvatar",avatarInput.files[0]);
    fetch( "/Webbasics_war_exploded/register/", {
      method: "PUT",
      headers: {},
      body: formData
    }).then( r => r.text() )
            .then( t => {
              console.log(t);
              if(t==="OK"){
                location.reload();
              }
            }) ;
  }
  function nameClick(e) {
    e.target.setAttribute( "contenteditable", "true" ) ;
    e.target.focus() ;
    e.target.savedText = e.target.innerText ;
  }
  function nameKeydown(e){
    if(e.keyCode===13){
      e.preventDefault();
      e.target.blur();
      return false;
    }
  }
  function nameBlur(e) {
    e.target.removeAttribute( "contenteditable" ) ;
    if( e.target.savedText !== e.target.innerText ) {
      if( confirm( "Сохранить изменения?" ) ) {
        const fieldName = e.target.getAttribute("data-field-name");
        const url = "/Webbasics_war_exploded/register/?"+fieldName+"=" + e.target.innerText;
        console.log( e.target.innerText ) ;
        //return;
        fetch( url, {
          method: "PUT",
          headers: {

          },
          body: ""
        }).then( r => r.text() )
                // OK // error
                .then( t => { console.log(t)
                if(t==="OK"){
                  location.reload();
                }
                else{
                  alert(t);
                  e.target.innerText=e.target.savedText;
                }
          }) ;
      }
      else {
        e.target.innerText = e.target.savedText ;
      }
    }
  }

</script>