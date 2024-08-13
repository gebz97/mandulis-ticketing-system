import { FC } from "react";
import { Controller, useForm } from "react-hook-form";
import { registerFormSchema, RegisterFormSchema } from "../api/register";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Card, Group, PasswordInput, TextInput } from "@mantine/core";

export const RegisterCard: FC = () => {
  const form = useForm<RegisterFormSchema>({
    resolver: zodResolver(registerFormSchema),
    defaultValues: {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      username: "",
    },
  });

  const handleSubmit = (data: RegisterFormSchema) => {
    console.log(data);
  };

  return (
    <Card withBorder p={30}>
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
        <Button type="submit" fullWidth mt="xl">
          Create my account
        </Button>
      </form>
    </Card>
  );
};
