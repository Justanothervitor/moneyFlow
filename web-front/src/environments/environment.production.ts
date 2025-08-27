import {HttpHeaders} from "@angular/common/http";

export const environment = {
  production: true,
  apiUrl: "https://4fd2d74746f9.ngrok-free.app/api/",
  httpOptions : {
    'headers': new HttpHeaders({'Content-Type':'application/json','Allow-Origin':'*','Access-Control-Allow-Origin':'true','ngrok-skip-browser-warning':'10'}),
  }
}
