import { FC } from "react";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Group, PasswordInput, TextInput } from "@mantine/core";
import {
  RegisterUserInput,
  registerUserInputSchema,
} from "../api/register-user";

interface RegisterFormProps {
  onSubmit: (data: RegisterUserInput) => void;
  isLoading?: boolean;
}

export const RegistrationForm: FC<RegisterFormProps> = ({
  onSubmit,
  isLoading = false,
}) => {
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

  return (
    <form onSubmit={form.handleSubmit(onSubmit)}>
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
      <Button type="submit" fullWidth mt="xl" loading={isLoading}>
        Create my account
      </Button>
    </form>
  );
};
