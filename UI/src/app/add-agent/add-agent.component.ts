import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';
import { User } from './User';

@Component({
  selector: 'app-add-agent',
  templateUrl: './add-agent.component.html',
  styleUrls: ['./add-agent.component.css']
})
export class AddAgentComponent implements OnInit {


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

  addAgent(event: Event){
    event.preventDefault()
    var user=new User()
    if(this.checkInputs()) return
    user.firstName=document.forms["add"]["firstName"].value
    user.lastName=document.forms["add"]["lastName"].value
    user.email=document.forms["add"]["email"].value
    user.username=document.forms["add"]["username"].value
    user.password=document.forms["add"]["password"].value
    this.Auth.addCommercialAgent(user,this.email,this.password).subscribe(data => {
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
    var firstName=document.forms["add"]["firstName"].value.trim()
    var lastName=document.forms["add"]["lastName"].value.trim()
    var email=document.forms["add"]["email"].value.trim()
    var username=document.forms["add"]["username"].value.trim()
    var password=document.forms["add"]["password"].value.trim()
    if(email==""||username==""||firstName==""||lastName==""||password==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    return false
  }
}
