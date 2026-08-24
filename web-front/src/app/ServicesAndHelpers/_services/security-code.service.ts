import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

const httpOptions = environment.httpOptions;
const API_END = environment.apiEndPointSecurityCodeService;

export class SecurityCodeService {

  constructor(private http: HttpClient) { }


}
