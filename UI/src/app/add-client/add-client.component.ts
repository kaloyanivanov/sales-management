import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';
import { Client } from './Client';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.css']
})
export class AddClientComponent implements OnInit {

  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  addClient(event: Event){
    event.preventDefault()
    if(this.checkInputs()) return
    var client=new Client()
    client.clientName=document.forms["add"]["client"].value
    client.email=document.forms["add"]["email"].value
    client.address=document.forms["add"]["address"].value
    client.phoneNumber=document.forms["add"]["phoneNumber"].value
    client.agentEmail=this.email
    this.Auth.addClient(this.email,this.password,client).subscribe(data => {
      this.message=data.message
      if(data.type=="message") this.colour="green"
      if(data.type=="error") this.colour="red"
      },
      (error)=>{
        this.colour="red"
        this.message="Error connecting the server"
    })
    
  }


  checkInputs(){
    var client=document.forms["add"]["client"].value.trim()
    var email=document.forms["add"]["email"].value.trim()
    var address=document.forms["add"]["address"].value.trim()
    var number=document.forms["add"]["phoneNumber"].value.trim()
    if(email==""||address==""||client==""||number==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    return false
  }
}
