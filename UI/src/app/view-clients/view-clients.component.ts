import { Component, OnInit } from '@angular/core';
import { Client } from '../add-client/Client';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-view-clients',
  templateUrl: './view-clients.component.html',
  styleUrls: ['./view-clients.component.css']
})
export class ViewClientsComponent implements OnInit {

  message:string
  email:string
  clients:Client[]=[]
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
    this.viewClients()
  }
  viewClients() {
    this.Auth.getClients(this.email,this.password).subscribe(result => {
      this.clients=result
    }, 
     (error) => this.message="Error connecting the server")
  }

}
