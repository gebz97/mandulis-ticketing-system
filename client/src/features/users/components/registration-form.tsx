import { FC } from "react";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Group, PasswordInput, TextInput } from "@mantine/core";
import {
  RegisterUserInput,
  registerUserInputSchema,
  useRegisterUser,
} from "../api/register-user";
import { RegisterUserResponseModel } from "../models/register-user-response";

interface RegisterFormProps {
  onSuccess: (data: RegisterUserResponseModel) => void;
}

export const RegistrationForm: FC<RegisterFormProps> = ({ onSuccess }) => {
  const register = useRegisterUser();

  const form = useForm<RegisterUserInput>({
    resolver: zodResolver(registerUserInputSchema),
    defaultValues: {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      username: "",
    },
  });

  const handleSubmit = (data: RegisterUserInput) => {
    register.mutate(
      { data: data },
      {
        onSuccess: onSuccess,
        onError: (error) => console.log(error),
      }
    );
  };

  return (
    <form onSubmit={form.handleSubmit(handleSubmit)}>
      <Group grow>
        <Controller
          control={form.control}
          name="firstName"
          render={({ field, fieldState: { error } }) => (
            <TextInput
              label="First Name"
              placeholder="Enter your first name"
              required
              error={error?.message}
              {...field}
            />
          )}
        />
        <Controller
          control={form.control}
          name="lastName"
          render={({ field, fieldState: { error } }) => (
            <TextInput
              label="Last Name"
              placeholder="Enter your last name"
              required
              error={error?.message}
              {...field}
            />
          )}
        />
      </Group>
      <Controller
        control={form.control}
        name="username"
        render={({ field, fieldState: { error } }) => (
          <TextInput
            label="Username"
            placeholder="Enter your username"
            required
            mt="md"
            error={error?.message}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="email"
        render={({ field, fieldState: { error } }) => (
          <TextInput
            label="Email"
            placeholder="Enter your email address"
            required
            mt="md"
            error={error?.message}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="password"
        render={({ field, fieldState: { error } }) => (
          <PasswordInput
            label="Password"
            placeholder="Enter your password"
            required
            mt="md"
            error={error?.message}
            {...field}
          />
        )}
      />
      <Button type="submit" fullWidth mt="xl" loading={register.isPending}>
        Create my account
      </Button>
    </form>
  );
};
