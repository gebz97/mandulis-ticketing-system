import { FC } from "react";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Anchor,
  Button,
  Checkbox,
  Group,
  PasswordInput,
  TextInput,
} from "@mantine/core";
import { LoginUserInput, loginUserInputSchema } from "../api/login-user";

interface LoginFormProps {
  onSubmit: (data: LoginUserInput) => void;
  isLoading?: boolean;
}

export const LoginForm: FC<LoginFormProps> = ({ onSubmit, isLoading }) => {
  const form = useForm<LoginUserInput>({
    resolver: zodResolver(loginUserInputSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  return (
    <form onSubmit={form.handleSubmit(onSubmit)}>
      <Controller
        control={form.control}
        name="username"
        render={({ field, fieldState: { error } }) => (
          <TextInput
            label="Username"
            placeholder="Enter your username"
            required
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
      <Group justify="space-between" mt="lg">
        <Checkbox label="Remember me" />
        <Anchor href="/user/forgot-password" size="sm">
          Forgot password?
        </Anchor>
      </Group>
      <Button type="submit" fullWidth mt="xl" loading={isLoading}>
        Log in
      </Button>
    </form>
  );
};
