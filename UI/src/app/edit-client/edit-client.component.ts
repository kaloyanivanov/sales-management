import { Component, OnInit } from '@angular/core';
import { Client } from '../add-client/Client';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';


@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.css']
})
export class EditClientComponent implements OnInit {
  loaded:boolean=false
  client:Client=new Client()
  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }
 
  getClient(event: Event){
    event.preventDefault()
    this.message=null
    if(this.checkEnterInputs()) return
    var email=document.forms["load"]["email"].value
    this.Auth.getClient(this.email,email,this.password).subscribe(data => {
      if(data!=null) {
        this.client.clientName=data.clientName
        this.client.email=data.email
        this.client.address=data.address
        this.client.phoneNumber=data.phoneNumber
        this.loaded=true}
     },
     (error)=>{
       this.colour="red"
       this.message="Error connecting the server"
   })
  }
  editClient(event: Event){
    event.preventDefault()
    this.message=null
    if(this.checkEditInputs())return
    var client:Client=new Client()
    var email=document.forms["load"]["email"].value
    client.clientName=document.forms["update"]["client"].value
    client.agentEmail=this.email
    client.email=document.forms["update"]["email_new"].value
    client.address=document.forms["update"]["address"].value
    client.phoneNumber=document.forms["update"]["number"].value
    this.Auth.editClient(this.email,email, client,this.password).subscribe(data => {
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
    var email=document.forms["load"]["email"].value.trim()
    if(email==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    return false
  }

  checkEditInputs()
{
  var client=document.forms["update"]["client"].value
  var email=document.forms["update"]["email_new"].value
  var address=document.forms["update"]["address"].value
  var number=document.forms["update"]["number"].value
  if(email==""||address==""||client==""||number==""){
    this.message="No empty fields allowed"
    this.colour="red"
    return true
  }
  return false
}
}
