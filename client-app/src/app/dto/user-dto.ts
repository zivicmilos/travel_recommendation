import { Location } from "../model/location-model";

export class UserDto {
    constructor(
        public name: string = '',
        public lastname: string = '',
        public username: string = '',
        public password: string = '',
        public email: string = '',
        public dateOfBirth: Date = new Date(),
        public status: string = '',
        public location: Location = new Location()
    ) { }
}