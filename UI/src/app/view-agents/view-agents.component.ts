import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LimitedUser } from '../LimitedUser';
import { LoginService } from '../login.service';
@Component({
  selector: 'app-view-agents',
  templateUrl: './view-agents.component.html',
  styleUrls: ['./view-agents.component.css']
})
export class ViewAgentsComponent implements OnInit {
  records:LimitedUser[]=[] 
  message:string
  email:string
  password:string

  constructor(private Auth:AuthorizationService, private data:LoginService) { 
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  ngOnInit(): void {
    this.viewAgents()
  }

  viewAgents(){
     this.Auth.getCommercialAgents(this.email,this.password).subscribe(res => {
      this.records=res
    }, 
    (error) => {
      console.log(error)
      this.message="Error connecting the server"}
    
    )
  }
}
