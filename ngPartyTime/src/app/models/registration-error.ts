export class RegistrationError extends Error {
  constructor(public override message: string, public statusCode: number) {
    super(message);
    this.name = 'RegistrationError';
  }
}
