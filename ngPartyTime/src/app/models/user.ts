export class User {
  id: number;
  username: string;
  password: string;
  enabled: boolean;
  createdAt: Date | null;
  role: string | null;
  firstName: string | null;
  lastName: string | null;
  updatedAt: Date | null;

  constructor(
    id: number = 0,
    username: string = '',
    password: string = '',
    enabled: boolean = true,
    createdAt: Date | null = null,
    role: string | null = null,
    firstName: string | null = null,
    lastName: string | null = null,
    updatedAt: Date | null = null
  ) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.updatedAt = updatedAt;
  }
}
