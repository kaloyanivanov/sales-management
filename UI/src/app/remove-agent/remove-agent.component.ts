import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-remove-agent',
  templateUrl: './remove-agent.component.html',
  styleUrls: ['./remove-agent.component.css']
})
export class RemoveAgentComponent implements OnInit {
  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  removeAgent(event: Event){
    event.preventDefault()
    if(this.checkInputs()) return
    var email=document.forms["remove"]["email"].value
    var username=document.forms["remove"]["username"].value
    this.Auth.removeCommercialAgent(email,username,this.email,this.password).subscribe(data =>{
      this.message=data.message
      if(data.type=="message") this.colour="green"
      if(data.type=="error") this.colour="red"
      } ,
      (error)=>{
        this.colour="red"
        this.message="Error connecting the server"
    })
  }


  checkInputs(){
    var email=document.forms["remove"]["email"].value.trim()
    var username=document.forms["remove"]["username"].value.trim()
    if(email==""||username==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    return false
  }

}
