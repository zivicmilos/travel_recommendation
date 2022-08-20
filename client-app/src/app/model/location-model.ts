import { Coordinates } from "./coordinates-model";

export class Location {
  constructor(
    public city: string = '',
    public country: string = '',
    public continent: string = '',
    public coordinates: Coordinates = new Coordinates()
  ) { }
}