import { Component, OnInit } from '@angular/core';
import { User } from '../add-agent/User';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-edit-agent',
  templateUrl: './edit-agent.component.html',
  styleUrls: ['./edit-agent.component.css']
})
export class EditAgentComponent implements OnInit {
  loaded:boolean=false
  agent:User=new User()
  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { 
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  ngOnInit(): void {
  }

  getAgent(event:Event){
    event.preventDefault()
    this.message=null
    if(this.checkEnterInputs()) return
    var username=document.forms["enter"]["username"].value
    var email=document.forms["enter"]["email"].value
    this.Auth.getAgent(username,email,this.email,this.password).subscribe(data => {
      if(data==null){
      this.message="No such commercial agent"
      this.colour="red"
      }
      if(data!=null) {
       this.agent.firstName=data.firstName
       this.agent.lastName=data.lastName
       this.agent.email=data.email
       this.agent.username=data.username
       this.agent.password=data.password

        this.loaded=true}
    },
    (error)=>{
      this.colour="red"
      this.message="Error connecting the server"
  })
}

editAgent(event: Event){
  event.preventDefault()
  this.message=null
  if(this.checkEditInputs())return
  var agent:User=new User()
  var username=document.forms["enter"]["username"].value
  var email=document.forms["enter"]["email"].value
  agent.firstName=document.forms["update"]["firstName"].value
  agent.lastName=document.forms["update"]["lastName"].value
  agent.username=document.forms["update"]["username"].value
  agent.email=document.forms["update"]["email"].value
  agent.password=document.forms["update"]["password"].value
  this.Auth.editAgent(username,email,agent,this.email,this.password).subscribe(data => {
    this.message=data.message
      if(data.type=="message") this.colour="green"
      if(data.type=="error") this.colour="red"
   },
   (error)=>{
     this.colour="red"
     this.message="Error connecting the server"
 })
}

checkEnterInputs(){
  var username=document.forms["enter"]["username"].value.trim()
  var email=document.forms["enter"]["email"].value.trim()
  if(email==""||username==""){
    this.message="No empty fields allowed"
    this.colour="red"
    return true
  }
  return false
}

checkEditInputs()
{
  var firstName=document.forms["update"]["firstName"].value
  var lastName=document.forms["update"]["lastName"].value
  var username=document.forms["update"]["username"].value
  var email=document.forms["update"]["email"].value
  var password=document.forms["update"]["password"].value
  if(email==""||username==""||firstName==""||lastName==""||password==""){
    this.message="No empty fields allowed"
    this.colour="red"
    return true
  }
  return false
}

}
