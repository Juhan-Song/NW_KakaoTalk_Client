# NW_KakaoTalk_Client

<h2> Requirements </h2>
----The all jar files below are also uploaded.----
<li> MySQL </li>
<li> json-simple-1.1.1.jar </li>
<li> mysql-connector-java-8.0.30.jar </li>

<h2> Table Required in mysql </h2>
<h4> Database Name: KakaoTalk </h4>
<h4> Table: userinfo { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  id       &nbsp;&nbsp;&nbsp;&nbsp; varchar(128), PK, NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  pwd      &nbsp;&nbsp;&nbsp;&nbsp; varchar(512), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  name     &nbsp;&nbsp;&nbsp;&nbsp; varchar(128), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  gender   &nbsp;&nbsp;&nbsp;&nbsp; varchar(10), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  nickname &nbsp;&nbsp;&nbsp;&nbsp; varchar(128), UQ, NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  birth    &nbsp;&nbsp;&nbsp;&nbsp; varchar(10), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  phone    &nbsp;&nbsp;&nbsp;&nbsp; varchar(13), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  email    &nbsp;&nbsp;&nbsp;&nbsp; varchar(256), UQ, NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  message  &nbsp;&nbsp;&nbsp;&nbsp; varchar(128) <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  login    &nbsp;&nbsp;&nbsp;&nbsp; datetime <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  logout   &nbsp;&nbsp;&nbsp;&nbsp; datetime <br>
} </h4>
<h4> Table: loggedin { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  nickname &nbsp;&nbsp;&nbsp;&nbsp; varchar(128), PK, NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  name     &nbsp;&nbsp;&nbsp;&nbsp; varchar(128), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  gender   &nbsp;&nbsp;&nbsp;&nbsp; varchar(10), NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  email    &nbsp;&nbsp;&nbsp;&nbsp; varchar(256), UQ, NN <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  login    &nbsp;&nbsp;&nbsp;&nbsp; datetime <br>
} </h4>

<h2> Youtube Presentation Link </h2>
https://www.youtube.com/watch?v=vMm5IMWagTw
