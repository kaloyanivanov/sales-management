import { Component, OnInit } from '@angular/core';
import { Product } from '../add-product/Product';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  loaded:boolean=false
  checked:boolean=false
  quantiyClass:string= "hidden"
  product:Product=new Product()
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

  getProduct(event: Event){
    event.preventDefault()
    if(this.checkEnterInputs()) return
    var id=document.forms["load"]["id"].value
    id=parseInt(id)
    this.Auth.getProduct(this.email,this.password,id).subscribe(data => {
      if(data==null){
        this.message="No such product agent"
        this.colour="red"
        }
      if(data!=null) {
        if(data.availability) this.quantiyClass= "show"
        if(!data.availability) this.quantiyClass= "hidden"
        this.checked=data.availability
        this.product.availability=data.availability
        this.product.id=data.id
        this.product.price=data.price
        this.product.quantity=data.quantity
        this.product.type=data.type
        this.loaded=true}
     },
     (error)=>{
       this.colour="red"
       this.message="Error connecting the server"
   })
   
  }

  editProduct(event: Event){
    event.preventDefault()
    this.message=null
    if(this.checkEditInputs())return
    var product= new Product()
    var id=document.forms["load"]["id"].value
    id=parseInt(id)
    var id_new=document.forms["update"]["id"].value
    product.id=parseInt(id_new)
    product.type=document.forms["update"]["type"].value
    product.price=document.forms["update"]["price"].value
    var quantiy=document.forms["update"]["quantity"].value
    if(this.checked)product.quantity=parseInt(quantiy)
    if(!this.checked)product.quantity=0
    product.availability=this.checked
    this.Auth.editProduct(this.email,this.password,product,id).subscribe(data => { 
      this.message=data.message
        if(data.type=="message") this.colour="green"
        if(data.type=="error") this.colour="red"
     },
     (error)=>{
       this.colour="red"
       this.message="Error connecting the server"
   })
  }


  checkEnterInputs(){
    var id=document.forms["load"]["id"].value.trim()
    if(id==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    if(!isFinite(id)){
      this.message="Id must be number"
      this.colour="red"
      return true
    }
    return false
  }

  checkEditInputs()
{
  var id=document.forms["update"]["id"].value.trim()
  var type=document.forms["update"]["type"].value.trim()
  var price=document.forms["update"]["price"].value.trim()
  var quantity
  if(this.checked)quantity=document.forms["update"]["quantity"].value.trim()
  if(!this.checked)quantity="0"

  if(id==""||type==""||price==""||quantity==null||quantity==""){
    this.message="No empty fields allowed"
    this.colour="red"
    return true
  }
  if(!isFinite(id)|| !isFinite(price)|| !isFinite(quantity)){
    this.message="Id, quantity and price must be numbers"
    this.colour="red"
    return true
  }
  return false
}
}
