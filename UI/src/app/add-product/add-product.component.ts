import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';
import { Product } from './Product';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  checked:boolean=false
  quantiyClass:string= "hidden"
  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  toggleSelection(event:Event){
    event.preventDefault
    this.checked=!this.checked
    if(this.checked) this.quantiyClass= "show"
    if(!this.checked) this.quantiyClass= "hidden"
  }

  addProduct(event:Event){
    event.preventDefault()
    if (this.checkInputs()) return
    var product= new Product()
    var id=document.forms["add"]["id"].value
    product.id=parseInt(id)
    product.type=document.forms["add"]["type"].value
    product.price=document.forms["add"]["price"].value
    var quantiy=document.forms["add"]["quantity"].value
    if(this.checked)product.quantity=parseInt(quantiy)
    if(!this.checked)product.quantity=0
    product.availability=this.checked
    this.Auth.addProduct(product,this.email,this.password).subscribe(data => {
    this.message=data.message
    if(data.type=="message") this.colour="green"
    if(data.type=="error") this.colour="red"
     },
     (error)=>{
       this.colour="red"
       this.message="Error connecting the server"
       console.log(error)
       
   })

  }

  checkInputs(){
    var id=document.forms["add"]["id"].value.trim()
    var type=document.forms["add"]["type"].value.trim()
    var price=document.forms["add"]["price"].value.trim()
    var quantity
    if(this.checked)quantity=document.forms["add"]["quantity"].value.trim()
    if(!this.checked){quantity="0"}

    if(id==""||type==""||price==""||quantity==null||quantity==""){
     console.log(quantity)
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    if(!isFinite(id)|| !isFinite(price)){
      this.message="Id and price must be numbers"
      this.colour="red"
      return true
    }
    return false
  }

}
