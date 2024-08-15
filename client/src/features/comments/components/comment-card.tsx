import { formatDate } from "@/utils/format";
import { Avatar, Box, Group, Stack, Text } from "@mantine/core";
import { FC } from "react";

interface CommentCardProps {
  id: string;
  username: string;
  content: string;
  date: string;
}

export const CommentCard: FC<CommentCardProps> = ({
  content,
  id,
  username,
  date,
}) => {
  return (
    <Box>
      <Group>
        <Avatar radius="xs" name={username} color="initials" />
        <div>
          <Text size="sm" fw="bold">
            {username}
          </Text>
          <Text size="xs" c="dimmed">
            {formatDate(Date.parse(date))}
          </Text>
        </div>
      </Group>
      <Text pl={54} pt="sm" size="sm">
        {content}
      </Text>
    </Box>
  );
};
