import {HttpHeaders} from "@angular/common/http";

export const environment = {
  production: true,
  apiUrl: "https://1e1d1a8b2986.ngrok-free.app/",
  httpOptions : {
    'headers': new HttpHeaders({'Content-Type':'application/json','Allow-Origin':'*','Access-Control-Allow-Origin':'true','ngrok-skip-browser-warning':'10'}),
  }
}
