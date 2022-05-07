import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private email= new BehaviorSubject<string>("No email")
  currentEmail=this.email.asObservable()
  private username= new BehaviorSubject<string>("No user")
  currentUsername=this.username.asObservable()
  private password= new BehaviorSubject<string>("No user")
  currentPassword=this.password.asObservable()

  constructor() { }

  setEmail(email:string){
    this.email.next(email)
  }

  setPassword(password:string){
    this.password.next(password)
  }

  setUsername(username:string){
    this.username.next(username)
  }
}
