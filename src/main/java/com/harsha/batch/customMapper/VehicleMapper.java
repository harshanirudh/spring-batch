package com.harsha.batch.customMapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.harsha.batch.model.Vehicle;

public class VehicleMapper implements FieldSetMapper<Vehicle>{

	@Override
	public Vehicle mapFieldSet(FieldSet fieldSet) throws BindException {
		// TODO Auto-generated method stub
		Vehicle vehicle= new Vehicle();
		vehicle.setRecordType(fieldSet.readString("Record Type"));
		vehicle.setVin(fieldSet.readString("VIN"));
		vehicle.setRegistrationClass(fieldSet.readString("Registration Class"));
		vehicle.setCity(fieldSet.readString("City"));
		vehicle.setState(fieldSet.readString("State"));
		vehicle.setZip(fieldSet.readString("Zip"));
		vehicle.setCounty(fieldSet.readString("County"));
		vehicle.setModelYear(fieldSet.readString("Model Year"));
		vehicle.setMake(fieldSet.readString("Make"));
		vehicle.setBodyType(fieldSet.readString("Body Type"));
		vehicle.setFuelType(fieldSet.readString("Fuel Type"));
		vehicle.setUnladenWeight(fieldSet.readString("Unladen Weight"));
		vehicle.setMaxGrossWeight(fieldSet.readString("Max Gross Weight"));
		vehicle.setPassengers(fieldSet.readString("Passengers"));
		vehicle.setRegValidDate(fieldSet.readString("Reg Valid Date"));
		vehicle.setExpirationDate(fieldSet.readString("Reg Expiration Date"));
		vehicle.setColor(fieldSet.readString("Color"));
		vehicle.setScoffflawIndicator(fieldSet.readString("Scofflaw Indicator"));
		vehicle.setSuspensionIndicator(fieldSet.readString("Suspension Indicator"));
		vehicle.setRevocationIndicator(fieldSet.readString("Revocation Indicator"));
		return vehicle;
	}

}
