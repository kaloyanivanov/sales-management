import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  username:string
  constructor(private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentUsername.subscribe(data=>this.username=data)
  }

}
