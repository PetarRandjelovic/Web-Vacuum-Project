import { FormControl } from '@angular/forms';

// Email validation function
export function emailValidator(control: FormControl): { [key: string]: any } | null {
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  if (control.value && !emailRegex.test(control.value)) {
    return { 'invalidEmail': true };
  }

  return null;
}
