import { Injectable } from '@angular/core';
import{HttpClient, HttpHeaders} from '@angular/common/http';
import { LimitedUser } from './LimitedUser';
import { User } from './add-agent/User';
import { Product } from './add-product/Product';
import { Sale } from './view-sales/Sales';
import { Client } from './add-client/Client';
import { Login } from './login/Login';

class Information{
  type:string
  message:string
}

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  constructor(private Http:HttpClient) { }


  getUser(login:Login,userEmail:string,password:string){
    return this.Http.post<Login>("http://localhost:8080/sales/login",login)
    
  }

  getCommercialAgents(userEmail:string,password:string){
    var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password
    return this.Http.get<LimitedUser[]>(URL,{responseType: 'json'})

  }

  addCommercialAgent(user:User,userEmail:string,password:string){
    var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password
    return this.Http.post<Information>(URL, user)
  }

removeCommercialAgent(email:string,username:string,userEmail:string,password:string){
  
  "{useremail}/{password}/remove/{username}/{email}"
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+"/remove/"+username+'/'+email
  return this.Http.delete<Information>(URL)
}

addProduct(product:Product,userEmail:string,password:string){
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+'/'+"add/product"
  return this.Http.post<Information>(URL, product)
}

removeProduct(id:number,userEmail:string,password:string){
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+'/'+"remove/product/"+id
  return this.Http.delete<Information>(URL)
}

getAgentSales(email:string){
  var URL="http://localhost:8080/sales/"+email
  return this.Http.get<Sale[]>(URL,{responseType: 'json'})

}

getSalesForPeriod(from:string,to:string,userEmail:string,password:string){
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+'/sale/'+from+'/'+to
  return this.Http.get<Sale[]>(URL,{responseType: 'json'})

}

getAllSales(userEmail:string,password:string){
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+'/sale'
  return this.Http.get<Sale[]>(URL,{responseType: 'json'})

}

removeSale(password:string,email:string, id:number){
  var URL="http://localhost:8080/sales/commercial/sales/"+email+'/'+password+'/'+id
  return this.Http.delete<Information>(URL)
}


getProduct(userEmail:string,password:string,id:number){
  var URL="http://localhost:8080/sales/admin/"+ userEmail+'/'+password + "/product/"+id
  return this.Http.get<Product>(URL)
  }

editProduct(userEmail:string,password:string,product:Product, id:number){
    var URL="http://localhost:8080/sales/admin/" + userEmail+'/'+password + "/update/product/"+id
    return this.Http.put<Information>(URL, product)
  
  }


addClient(userEmail:string,password:string,client:Client){
  var URL="http://localhost:8080/sales/commercial/" + userEmail+'/'+password + "/client"
  return this.Http.post<Information>(URL, client)
}

removeClient(agentEmail:string,email:string,password:string){
  var URL="http://localhost:8080/sales/commercial/client/"+agentEmail+'/'+password+'/'+email
  return this.Http.delete<Information>(URL)
}

getClient(agentEmail:string,email:string,password:string){
var URL="http://localhost:8080/sales/commercial/client/"+agentEmail+'/'+password+'/'+email
return this.Http.get<Client>(URL)
}

getClients(agentEmail:string,password:string){
  var URL="http://localhost:8080/sales/commercial/client/"+agentEmail+'/'+password
  return this.Http.get<Client[]>(URL,{responseType: 'json'})

}

addSale(userEmail:string,password:string,sale:Sale){
  var URL="http://localhost:8080/sales/commercial/"+userEmail+'/'+password+'/sale'
  
  return this.Http.post<Information>(URL,sale)
}

getSale(email:string,id:number,password:string){
  var URL="http://localhost:8080/sales/commercial/sales/"+email+'/'+password+'/'+id
  return this.Http.get<Sale>(URL)
  }

editClient(agentEmail:string,email:string, client:Client,password:string){
  var URL="http://localhost:8080/sales/commercial/client/"+agentEmail+'/'+password+'/'+email
  return this.Http.put<Information>(URL, client)

}

editSale(email:string,id:number, sale:Sale,password:string){
  var URL="http://localhost:8080/sales/commercial/sales/"+email+'/'+password+'/'+id
  return this.Http.put<Information>(URL, sale)

}

getAgent(username:string,email:string,userEmail:string,password:string){
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+'/'+username+'/'+email
  return this.Http.get<User>(URL)

}

editAgent(username:string,email:string,agent:User,userEmail:string,password:string){
  var URL="http://localhost:8080/sales/admin/"+userEmail+'/'+password+'/edit/'+username+'/'+email
  return this.Http.put<Information>(URL, agent)

}

getProducts(){
  var URL="http://localhost:8080/sales/products"
  return this.Http.get<Product[]>(URL,{responseType: 'json'})

}


}
