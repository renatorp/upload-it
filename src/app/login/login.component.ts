import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.user = new User();
  }

  authenticate() {
    if (this.user.name && this.user.password) {
      this.userService.validateUser(this.user).subscribe(
        response => {
           const user = <User>response;
           localStorage.setItem('userId', user.id.toString());
           this.router.navigate(['/upload']);
        },
        error => {}
      );
    }
  }
}
