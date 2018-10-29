import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(private toastrService: ToastrService) { }

  public handleError(err: any) {
    console.log(`Operation failed: ${err.message}`);

    if (err.status === 401 || err.status === 403) {
      this.toastrService.error('Usuário Não Autorizado.');
      return;
    }

    if (('' + err.status).startsWith('4')) {
      this.toastrService.error(err.error.message);
      return;

    }
      this.toastrService.error('Ocorreu um erro inesperado.');
  }
}
