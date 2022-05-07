import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AgentAuthGuard } from '../auth.guard';
import { AdminAuthGuard } from '../auth.guard2';
import {AuthorizationService} from '../authorization.service'
import { LoginService } from '../login.service';
import { Login } from './Login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  login:Login=new Login()
  constructor(private router: Router,private Auth:AuthorizationService,private data:LoginService) {}
  ngOnInit(): void {
  }

  
  userLogin(event: Event){
    event.preventDefault()
    const email=document.forms["login"]["email"].value
    const password=document.forms["login"]["password"].value
    this.login.email=email
    this.login.password=password
    this.Auth.getUser(this.login).subscribe(res => {
      if(res.type=="admin") {
        this.data.setEmail(res.email)
        this.data.setUsername(res.username)
        this.data.setPassword(res.password)
        this.router.navigate(["/admin"])
        AdminAuthGuard.enter=true
      }
      if(res.type=="agent") {
        this.data.setEmail(res.email)
        this.data.setUsername(res.username)
        this.data.setPassword(res.password)
        this.router.navigate(["/commercial"])
        AgentAuthGuard.enter=true
      }
    }
    )

    
  }

}
