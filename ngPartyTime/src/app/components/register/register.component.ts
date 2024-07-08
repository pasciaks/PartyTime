import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { FormsModule, NgForm } from '@angular/forms';
import { RegistrationError } from '../../models/registration-error';
import { CommonModule } from '@angular/common';
import { userSchema } from './validation-schema'; // Adjust the path accordingly
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as Yup from 'yup';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent implements OnInit {
  //FIELDS
  newUser: User = new User();

  errors: string[] = [];

  //CONSTRUCTOR
  constructor(private authService: AuthService, private router: Router) {}

  //LIFECYCLE HOOKS
  ngOnInit(): void {}

  register = async (form: NgForm) => {
    this.errors = [];

    // Yup validation

    let result = null;

    try {
      result = await userSchema.validate(this.newUser, { abortEarly: false });
      console.log('Form Submitted!', this.newUser);
      // Handle form submission
    } catch (err: Yup.ValidationError | any) {
      if (err instanceof Yup.ValidationError) {
        err.inner.forEach((error) => {
          console.log(error.path, error.message);
          this.errors.push(error?.path + ':' + error.message);
        });
      }
      console.log('Form is invalid', this.errors);
      return;
    }

    console.log('result', result);

    // custom JS validation
    if (this.newUser.username.length < 3) {
      this.errors.push('Username must be at least 3 characters long.');
      return;
    }

    if (form.valid) {
      console.log('Form Submitted!', this.newUser);
      this.registerBackEnd(this.newUser);
    } else {
      console.log('Form is invalid');
    }
  };

  //OTHER METHODS
  registerBackEnd(newUser: User): void {
    //console.log(newUser); //test purposes for data binding confirmation
    this.authService.register(newUser).subscribe({
      next: (user) => {
        console.log('Registration successful', user);
        this.authService.login(newUser.username, newUser.password).subscribe({
          next: () => {
            return this.router.navigateByUrl('welcome');
          },
          error: (error) => {
            alert('Immediate Login after Registration failed');
            console.error('Login failed', error);
            return;
          },
        });
      },
      error: (error: RegistrationError) => {
        if (error.statusCode === 409) {
          alert('Username already exists. Please try a different username.');
        } else if (error.statusCode === 400) {
          alert('Invalid registration data. Please try again.');
        } else {
          alert('Registration failed : ' + JSON.stringify(error, null, 2));
        }
        console.error('Registration failed', error);
        return;
      },
    });
  }
}
