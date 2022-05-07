import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';


@Component({
  selector: 'app-commercial',
  templateUrl: './commercial.component.html',
  styleUrls: ['./commercial.component.css']
})
export class CommercialComponent implements OnInit {
  username:string
  
  constructor(private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentUsername.subscribe(data=>this.username=data)
  }

}
