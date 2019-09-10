package com.harsha.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.harsha.batch.customMapper.VehicleMapper;
import com.harsha.batch.listener.vehicleListener;
import com.harsha.batch.model.Vehicle;

@Configuration
@EnableBatchProcessing
public class VehicleConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public FlatFileItemReader<Vehicle> vehicleReader(){
		String 	filePath="D:\\vehicle-snowmobile-and-boat-registrations.csv";
		String headerString="Record Type,VIN,Registration Class,City,State,Zip,County,Model Year,Make,Body Type,Fuel Type,Unladen Weight,Max Gross Weight,Passengers,Reg Valid Date,Reg Expiration Date,Color,Scofflaw Indicator,Suspension Indicator,Revocation Indicator";
		String header[]=headerString.split(",");
		return new FlatFileItemReaderBuilder<Vehicle>()
				.name("vehicleReader")
				.resource(new FileSystemResource(filePath))
				.linesToSkip(1)
				.delimited()
				.names(header)
				.fieldSetMapper(new VehicleMapper())
				.build();		
	}
	
	@Bean
	public JdbcBatchItemWriter<Vehicle> vehicleWriter(DataSource ds){
		return new JdbcBatchItemWriterBuilder<Vehicle>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO vehicles(recordType,vin,registrationClass,city,state,zip,county,modelYear,make,bodyType,fuelType,unladenWeight,maxGrossWeight,passengers,regValidDate,expirationDate,color,scoffflawIndicator,suspensionIndicator,revocationIndicator) VALUES(:recordType,:vin,:registrationClass,:city,:state,:zip,:county,:modelYear,:make,:bodyType,:fuelType,:unladenWeight,:maxGrossWeight,:passengers,:regValidDate,:expirationDate,:color,:scoffflawIndicator,:suspensionIndicator,:revocationIndicator)")
				.dataSource(ds)
				.build();
	}
	
	@Bean
	public Job vehicleImportJob(vehicleListener listener, Step vehicleStep1) {
		return jobBuilderFactory
				.get("vehicleImportJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(vehicleStep1)
				.end()
				.build();
	}
	@Bean
	public Step vehicleStep1(JdbcBatchItemWriter<Vehicle> writer) {
		return stepBuilderFactory.get("vehicleStep1")
				.<Vehicle,Vehicle> chunk(10000)
				.reader(vehicleReader())
				.writer(writer)
				.taskExecutor(taskExecutor())
				.throttleLimit(10)
				.build();
	}
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor executor= new SimpleAsyncTaskExecutor("Async Batch ");
		executor.setConcurrencyLimit(10);
		return executor;
	}
}
