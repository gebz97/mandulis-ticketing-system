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
import {
  LoginUserInput,
  loginUserInputSchema,
  useLoginUser,
} from "../api/login-user";

interface LoginFormProps {
  onSuccess: () => void;
}

export const LoginForm: FC<LoginFormProps> = ({ onSuccess }) => {
  const login = useLoginUser();

  const form = useForm<LoginUserInput>({
    resolver: zodResolver(loginUserInputSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const handleSubmit = (data: LoginUserInput) => {
    login.mutate(
      { data: data },
      {
        onSuccess: onSuccess,
      }
    );
  };

  return (
    <form onSubmit={form.handleSubmit(handleSubmit)}>
      <Controller
        control={form.control}
        name="email"
        render={({ field, fieldState: { error } }) => (
          <TextInput
            label="Email"
            placeholder="Enter your email address"
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
      <Button type="submit" fullWidth mt="xl" loading={login.isPending}>
        Log in
      </Button>
    </form>
  );
};
