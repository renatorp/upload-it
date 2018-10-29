import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorHandlerService } from '../error-handler.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User;

  constructor(private userService: UserService, private router: Router, private errorHandler: ErrorHandlerService) { }

  ngOnInit() {
    this.user = new User();
  }

  authenticate() {
      this.userService.validateUser(this.user).subscribe(
        response => {
           const user = <User>response;
           localStorage.setItem('userId', user.id.toString());
           this.router.navigate(['/upload']);
        },
        error => { this.errorHandler.handleError(error); }
      );
  }
}
